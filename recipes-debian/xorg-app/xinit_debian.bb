require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/xinit.inc
DEBIAN_PATCH_TYPE = "quilt"

SUMMARY = "Runtime configuration and test of XInput devices"

DESCRIPTION = "Xinput is an utility for configuring and testing XInput devices"

LIC_FILES_CHKSUM = "file://COPYING;md5=18f01e7b39807bebe2b8df101a039b68"

DEPENDS += " libxi libxrandr libxinerama"
