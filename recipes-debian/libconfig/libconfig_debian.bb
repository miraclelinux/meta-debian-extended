# base recipe: meta-oe/recipes-extended/libconfig/libconfig_1.7.2.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

SUMMARY = "C/C++ Configuration File Library"
DESCRIPTION = "Library for manipulating structured configuration files"
HOMEPAGE = "https://hyperrealm.github.io/libconfig/"
BUGTRACKER = "https://github.com/hyperrealm/libconfig/issues"
SECTION = "libs"

LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=fad9b3332be894bab9bc501572864b29"

inherit debian-package
require recipes-debian/sources/libconfig.inc

inherit autotools-brokensep pkgconfig

FILES_${PN}_append = " \
    ${libdir}/cmake \
"
