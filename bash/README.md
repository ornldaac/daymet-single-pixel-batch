# Automate Downloads of Multiple Daymet Single Pixel Data
This repo contains the necessary bash program for automating the download of multiple locations of [Daymet data](https://daymet.ornl.gov/single-pixel/) with one script.

Note:
*This has been improved from the script within daymet.zip. Now you can subset Daymet Single Pixel Data on Daymet years and [variables](https://github.com/ornldaac/daymet-single-pixel-batch#daymet-variables)*

## Example Usage
1. Download `daymet_spt_batch.sh` and `latlon.txt` in this directory (make sure you keep them in the same directory).
2. Update the `latlon.txt` file with your lat/lon pairs of interest (you can also [specify variables and years](https://github.com/ornldaac/daymet-single-pixel-batch#sample-text-files)).
3. Run the following command:
```bash
$ ./daymet_spt_batch.sh latlon.txt
```

Here is a sample run:
```bash
$ ls
daymet_spt_batch.sh   latlon.txt   README.md    

$ cat latlon.txt
Variables: tmin, tmax, prcp
years: 2012,2013,2014
file1.csv, 45.0,   -97.0
45.3, -97.0
46.3, -97
42.1, -101
50,-95

$ ./daymet_spt_batch.sh latlon.txt
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=45.0&lon=-97.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=45.3&lon=-97.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=46.3&lon=-97.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=42.1&lon=-101.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=50.0&lon=-95.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014

$ ls
11920_lat42.1_lon-101.0_2018-09-04_045627.csv	12643_lat50.0_lon-95.0_2018-09-04_045627.csv	latlon.txt
12102_lat45.3_lon-97.0_2018-09-04_045627.csv	daymet_spt_batch.sh
12282_lat46.3_lon-97.0_2018-09-04_045627.csv	file1.csv  README.md
```
