#! /usr/bin/env python
from __future__ import print_function
import os
import sys
import requests

if (len(sys.argv) < 2):
    print("Error: Expected input file")
    print("Usage: $ python daymet_multiple_extraction.py FileName")
    sys.exit()

STARTYEAR = 1980
ENDYEAR   = 2018
NO_NAME = "NULL"
YEAR_LINE = "years:"
VAR_LINE  = "variables:"
DAYMET_VARIABLES = ['dayl', 'prcp', 'srad', 'swe', 'tmax', 'tmin', 'vp']
DAYMET_YEARS     = [str(year) for year in range(STARTYEAR, ENDYEAR + 1)]
DAYMET_URL_STR = r'https://daymet.ornl.gov/single-pixel/api/data?lat={}&lon={}'

def parse_params(line, param_list):
    start_idx = line.index(":") + 1
    line_split = line[start_idx:].split(",")
    requested_params = []
    for elem in line_split:
        if elem in param_list:
            requested_params.append(elem)
    return ",".join(requested_params)

inF = open(sys.argv[1])
lines = inF.read().lower().replace(" ", "").split("\n")
inF.close()

lats  = []
lons  = []
names = []
requested_vars = ",".join(DAYMET_VARIABLES)
requested_years = ",".join(DAYMET_YEARS)

for line in lines:
    line = line.lower()
    if line:
        if VAR_LINE in line:
            requested_vars = parse_params(line, DAYMET_VARIABLES)
        elif YEAR_LINE in line:
            requested_years = parse_params(line, DAYMET_YEARS)
        else:
            line_split = line.split(",")
            if (len(line_split) > 2):
                names.append(line_split[0])
                lats.append(line_split[1])
                lons.append(line_split[2])
            else:
                names.append(NO_NAME)
                lats.append(line_split[0])
                lons.append(line_split[1])

var_str = ''
if requested_vars:
    var_str = "&measuredParams=" + requested_vars

years_str = ''
if requested_years:
    years_str = "&year=" + requested_years



num_files_requested = len(lats)
num_downloaded = 0
for i in range(num_files_requested):
    curr_url = DAYMET_URL_STR.format(lats[i], lons[i]) + var_str + years_str
    print("Processing:", curr_url)
    res = requests.get(curr_url)
    if not res.ok:
        print("Could not access the following URL:", curr_url)
    else:
        if names[i] == "NULL":
            outFname = res.headers["Content-Disposition"].split("=")[-1]
        else:
            outFname = names[i]
        text_str = res.content
        outF = open(outFname, 'wb')
        outF.write(text_str)
        outF.close()
        res.close()
        num_downloaded += 1

print("Finished downloading", num_downloaded, "files.")
