# Batch Download of Daymet Single Pixel Data
This repo contains methods for automating the download of multiple locations of [Daymet Single Pixel data](https://daymet.ornl.gov/single-pixel/) at once. Daymet web-service documentation can be found [here](https://daymet.ornl.gov/web_services.html#single_pixel_data_extraction).

### Methods
1. [Java](https://github.com/kvgarimella/daymet-single-pixel-batch/tree/master/java)
2. [Python](https://github.com/kvgarimella/daymet-single-pixel-batch/tree/master/python)


## Sample Text Files
In order to download several data points at once, we will create a file that specifies each coordinate we would like to download. This file can also specify Daymet variables and Daymet years (1980 - most recently published year) that you would like. Let's assume the file name is `latlon.txt` but it can be your choosing.

Example 1: Simple pairs of coordinates:
```bash
45.3, -97.0
46.3, -97.0
47.3, -97.0
```

Example 2: Providing a name for each coordinate that will be downloaded:
```bash
file1.csv, 45.3, -97.0
file2.csv, 46.3, -97.0
file3.csv, 47.3, -97.0
```

Example 3: Specifying variables:
```bash
Variables: tmin, prcp, dayl
45.3, -97.0
46.3, -97.0
47.3, -97.0
```

Example 4: Specifying years:
```bash
Years: 2012, 2013, 2014, 2015, 2016
45.3, -97.0
46.3, -97.0
47.3, -97.0
```

Example 5: Specifying variables and years:
```bash
Variables: tmin, prcp, dayl
Years: 2012, 2013, 2014, 2015, 2016
45.3, -97.0
46.3, -97.0
47.3, -97.0
```

Example 6: A coordinate to be saved as a specified filename. The first two points will be saved as `file1.csv` and `file2.csv`, while the third will be saved as the name given by the Daymet server (`tileID_lat_lon_time.csv`).
```bash
Variables: tmin, prcp, dayl
Years: 2012, 2013, 2014, 2015, 2016
file1.csv, 45.3, -97.0
file2.csv, 46.3, -97.0
47.3, -97.0
```

### Daymet Variables:
| Variable Name |                Description (units)               |
|---------------|:------------------------------------------------:|
|      dayl     |   Duration of the daylight period (seconds/day)  |
|      prcp     |        Daily total precipitation (mm/day)        |
|      srad     | Incident shortwave radiation flux density(W/m<sup>2</sup>) |
|      swe      |          Snow water equivalent (kg/m<sup>2</sup>)          |
|      tmax     |    Daily maximum 2-meter air temperature (°C)    |
|      tmin     |    Daily minimum 2-meter air temperature (°C)    |
|       vp      |             Water vapor pressure (Pa)            |

### Daymet Years:
1980 - the most recent published year
