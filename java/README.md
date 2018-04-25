# Automate Downloads of Multiple Daymet Single Pixel Data
This repo contains the necessary Java program for automating the download of multiple locations of [Daymet data](https://daymet.ornl.gov/single-pixel/) with one script. This will require you to have [Java installed on your machine](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

Note:
*This has been improved from the script within daymet.zip. Now you can subset Daymet Single Pixel Data on Daymet years and [variables](https://github.com/ornldaac/daymet-single-pixel-batch#daymet-variables)*

## Example Usage
1. Download all the files in this directory (make sure you keep them in the same directory).
2. Update the `latlon.txt` file with your lat/lon pairs of interest (you can also [specify variables and years](https://github.com/ornldaac/daymet-single-pixel-batch#sample-text-files)).
3. Run the following command:
```bash
$ bash daymet_multiple_extraction.sh
```
**OR**

```bash
$ java -jar -Xms512m -Xmx1024m -Dhttps.protocols=TLSv1.1,TLSv1.2 daymet_multiple_extraction.jar latlon.txt
```

Here is a sample run:
```bash
$ ls
Daymet.java   daymet_multiple_extraction.jar   latlon.txt
README.md     daymet_multiple_extraction.sh    manifest.mf

$ cat latlon.txt
Variables: tmin, tmax, prcp
years: 2012,2013,2014
file1.csv, 45.0,   -97.0
45.3, -97.0
46.3, -97
42.1, -101
50,-95

$ bash daymet_multiple_extraction.sh
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=45.0&lon=-97.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=45.3&lon=-97.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=46.3&lon=-97.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=42.1&lon=-101.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Processing: https://daymet.ornl.gov/single-pixel/api/data?lat=50.0&lon=-95.0&measuredParams=tmin,tmax,prcp&year=2012,2013,2014
Finished downloading 5 files.

$ ls
11920_lat42.1_lon-101.0_2018-09-04_045627.csv	12643_lat50.0_lon-95.0_2018-09-04_045627.csv	latlon.txt
12102_lat45.3_lon-97.0_2018-09-04_045627.csv	daymet_multiple_extraction.jar
12282_lat46.3_lon-97.0_2018-09-04_045627.csv	file1.csv  README.md
```

## Making Changes
If you wish to make changes to the Java code, you will need to re-compile and update the daymet_multiple_extraction.jar file. You will need to have the `manifest.mf` file in the same directory that you compile from.

```bash
$ javac Daymet.java
$ jar cfm daymet_multiple_extraction.jar manifest.mf Daymet.class
```
