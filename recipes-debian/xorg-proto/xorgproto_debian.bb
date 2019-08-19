# base recipe: meta/recipes-graphics/xorg-proto/xorgproto_2018.4.bb
# base branch: warrior
# base commit: cc9a768192d3c07af815a494c32bb3edfb2e4726

inherit debian-package
require recipes-debian/sources/xorgproto.inc
DEBIAN_PATCH_TYPE="nopatch"
FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/xorg-proto/xorgproto"

require xorg-proto-common.inc

SUMMARY = "XCalibrate: Touchscreen calibration headers"

DESCRIPTION = "This package provides the headers and specification documents defining \
the core protocol and (many) extensions for the X Window System"

LICENSE = "MIT-style"
LIC_FILES_CHKSUM = "file://COPYING-x11proto;md5=b9e051107d5628966739a0b2e9b32676"

SRC_URI += "file://0001-Remove-libdir-specification.patch"

BBCLASSEXTEND = "native nativesdk"
