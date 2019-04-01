# Sector-based Indoor Positioning

An alternative approach in WiFi Indoor Positioning with low calibration efforts

One of the big problems with indoor positioning systems is the trade-off between calculation/preparation costs and the corresponding accuracy of the position information. Instead of aiming at high accuracy – which is not necessary in many scenarios – sector-based positioning systems trade a low accuracy (building sectors rather than points) for a robust and cheap position estimation.

## Goal

The goal of this project is to analyze the possibilities of an alternative way of indoor positioning, which delivers a sector-based position rather than a point-based position. This alternative way requires minimal or no preparation phases to establish a robust and coarse indoor positioning system. Furthermore, it should be analyzed how accurate a system in such a configuration performs.

This project has been conducted at the Master's degree program of Mobile Computing in the Department for Mobility & Energy at the University of Applied Sciences Upper Austria in Hagenberg, Austria.

## Motivation

One of the biggest problems in WiFi indoor positioning systems are the high efforts in the preparation/pre-calibration phase. Traditional methods use fingerprinting techniques: the building is divided into a grid and for each grid cell the WiFi signals are measured. The built database can then be used for the position estimation, which delivers usually a position with a high accuracy.

High accuracy inside of buildings is often not needed. This is especially the case in large buildings, e.g. airports, railway stations, shopping malls etc. Most of the time a sector-based position is enough to support the user during orientation within buildings.
With the knowledge of the locations of the WiFi access points and the detected access points on-site it should be possible to estimate a sector-based position of the user.

Such a developed system would greatly improve the orientation within buildings with minimal costs for the provider of the positioning system. Use cases of such a system are, as already mentioned, any large buildings, where positioning is needed for the orientation, e.g. airports or railway stations.
