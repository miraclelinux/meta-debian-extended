# base recipe: meta-openembedded/meta-oe/recipes-devtools/doxygen/doxygen_1.8.15.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

DESCRIPTION = "Doxygen is the de facto standard tool for generating documentation from annotated C++ sources."
HOMEPAGE = "http://www.doxygen.org/"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "flex-native bison-native"

inherit debian-package

SRC_URI += "file://0001-build-don-t-look-for-Iconv.patch"

inherit cmake python3native
require recipes-debian/sources/doxygen.inc

BBCLASSEXTEND = "native"
