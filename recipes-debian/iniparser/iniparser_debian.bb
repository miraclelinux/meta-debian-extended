# base recipe: meta-openembedded/meta-oe/recipes-support/iniparser/iniparser_4.1.bb
# base branch: zeus
# base commit: 2b5dd1eb81cd08bc065bc76125f2856e9383e98b

SUMMARY = "The iniParser library is a simple C library offering INI file parsing services (both reading and writing)."
SECTION = "libs"
HOMEPAGE = "https://github.com/ndevilla/iniparser"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e02baf71c76e0650e667d7da133379ac"

DEPENDS = "doxygen-native"

inherit debian-package

# tag 4.1
SRCREV= "0a38e85c9cde1e099ca3bf70083bd00f89c3e5b6"

inherit cmake
require recipes-debian/sources/iniparser.inc
