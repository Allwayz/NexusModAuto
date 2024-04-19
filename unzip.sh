#!/bin/bash

for i in $(ls ./mod/*.zip);
do tar -xf $i -C ./mod; 
done;
