#!/bin/bash
#Daymet Constants
years="1980,1981,1982,1983,1984,1985,1986,1987,1988,1989"
years+=",1990,1991,1992,1993,1994,1995,1996,1997,1998,1999"
years+=",2000,2001,2002,2003,2004,2005,2006,2007,2008,2009"
years+=',2010,2011,2012,2013,2014,2015,2016,2017,2018,2019'
years+=',2020'
vars="dayl,prcp,srad,swe,tmax,tmin,vp"
# read in first command line argument (should be latlon.txt)
inF=$1

# check if first command line argument exists
if [ -z $inF ]
then
  echo Error: No input file provided
  echo Usage: ./run.sh inF
  exit 1
fi

inFs=()
lats=()
lons=()
while read p; do
  IFS=;
  if grep -q "[yY]ears" <<< $p; then
    years=$(cut -d":" -f2 <<<$p | tr -d "[:space:]")
  else
    if grep -q "[vV]ariables" <<< $p; then
      vars=$(cut -d":" -f2 <<<$p | tr -d "[:space:]")
    else
      if [ ! -z $p ]; then
        arr=$(echo $p | tr -d "[:space:]")
        IFS=,;
        ary=($arr)
        len=$(echo ${#ary[@]})
        if [ $len -ge 3 ]; then
          inFs+=(${ary[0]})
          lats+=(${ary[1]})
          lons+=(${ary[2]})
        else
          inFs+=(NULL)
          lats+=(${ary[0]})
          lons+=(${ary[1]})
        fi
      fi
    fi
  fi
  IFS=;

done < $inF

if [ -x "$(command -v curl)" ]; then
  downloadwName() { curl -J -O --silent $1; }
  download() { curl --silent $1 > $2; }
else
  if [ -x "$(command -v wget)" ]; then
    downloadwName() { wget --content-disposition -q $1; }
    download() { wget -q $1 -o $2; }
  else
    echo "Error: You must have either curl or wget installed to run this program."
    exit 1
  fi
fi

for key in "${!lats[@]}"; do
  echo Processing https://daymet.ornl.gov/single-pixel/api/data?lat=${lats[$key]}\&lon=${lons[$key]}\&measuredParams=$vars\&year=$years
  if [ ! ${inFs[$key]} == NULL ]; then
    download https://daymet.ornl.gov/single-pixel/api/data?lat=${lats[$key]}\&lon=${lons[$key]}\&measuredParams=$vars\&year=$years ${inFs[$key]}
  else
    downloadwName https://daymet.ornl.gov/single-pixel/api/data?lat=${lats[$key]}\&lon=${lons[$key]}\&measuredParams=$vars\&year=$years
  fi
done
echo ""
