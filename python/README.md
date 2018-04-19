# Automate Downloads of Multiple Daymet Single Pixel Data
This repo contains the necessary Python program for automating the download of multiple locations of [Daymet data](https://daymet.ornl.gov/single-pixel/) with one script. This will require you to have [Python installed on your machine](https://www.python.org/downloads/).

Note:
*This has been improved from the script within daymet.zip. Now you can subset Daymet Single Pixel Data on Daymet years and [variables](https://github.com/kvgarimella/daymet-single-pixel-batch#daymet-variables)*

## Example Usage
1. Download all the files in this directory (make sure you keep them in the same directory).
2. Update the `latlon.txt` file with your lat/lon pairs of interest (you can also [specify variables and years](https://github.com/kvgarimella/daymet-single-pixel-batch#sample-text-files)).
3. Run the following command:
```bash
$ pip install -r requirements.txt
$ python daymet_multiple_extraction.py latlon.txt
```

Here is a sample run:
```bash
$ ls
daymet_multiple_extraction.py   latlon.txt   README.md    

$ cat latlon.txt
Variables: tmin, tmax, prcp
years: 2012,2013,2014
file1.csv, 45.0,   -97.0
45.3, -97.0
46.3, -97
42.1, -101
50,-95

$ python daymet_multiple_extraction.py latlon.txt
Processing: https://daymet.ornl.gov/single-pixel/send/send/saveData?lat=45.0&lon=-97.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/send/send/saveData?lat=45.3&lon=-97.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/send/send/saveData?lat=46.3&lon=-97.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/send/send/saveData?lat=42.1&lon=-101.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/send/send/saveData?lat=50.0&lon=-95.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Finished downloading 5 files.

$ ls
11920_lat42.1_lon-101.0_2018-09-04_045627.csv	12643_lat50.0_lon-95.0_2018-09-04_045627.csv	latlon.txt
12102_lat45.3_lon-97.0_2018-09-04_045627.csv	daymet_multiple_extraction.py
12282_lat46.3_lon-97.0_2018-09-04_045627.csv	file1.csv  README.md
```
