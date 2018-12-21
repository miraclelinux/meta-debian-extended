require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

SUMMARY = "XTest: X Test extension library"

DESCRIPTION = "This extension is a minimal set of client and server \
extensions required to completely test the X11 server with no user \
intervention."

LICENSE = "MIT-style"
LIC_FILES_CHKSUM = "file://COPYING;md5=bb4f89972c3869f617f61c1a79ad1952 \
                    file://src/XTest.c;beginline=2;endline=32;md5=b1c8c9dff842b4d5b89ca5fa32c40e99"

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libxtst.inc
DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"

DEPENDS += "libxext xorgproto libxi"
PROVIDES = "xtst"

XORG_PN = "libXtst"
