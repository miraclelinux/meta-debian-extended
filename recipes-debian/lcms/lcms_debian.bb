# base recipe: meta-oe/recipes-support/lcms/lcms_2.7.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "Little cms is a small-footprint, speed optimized color management engine"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=6c786c3b7a4afbd3c990f1b81261d516"

require recipes-debian/sources/lcms2.inc
inherit debian-package

DEBIAN_UNPACK_DIR = "${WORKDIR}/lcms2-${PV}"

DEPENDS = "tiff"

BBCLASSEXTEND = "native"

inherit autotools
