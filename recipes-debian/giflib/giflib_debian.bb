# base recipe: meta-oe/recipes-devtools/giflib/giflib_5.1.4.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "shared library for GIF images"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=ae11c61b04b2917be39b11f78d71519a"

SRC_URI = "${SOURCEFORGE_MIRROR}/giflib/${BP}.tar.bz2"

inherit debian-package
require recipes-debian/sources/giflib.inc

inherit autotools

PACKAGES += "${PN}-utils"
FILES_${PN} = "${libdir}/libgif.so.*"
FILES_${PN}-utils = "${bindir}"

BBCLASSEXTEND = "native"

RDEPENDS_${PN}-utils = "perl"
