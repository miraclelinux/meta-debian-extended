# base recipe: meta/recipes-graphics/xorg-lib/libxres_1.2.0.bb
# base branch: warrior
# base commit: 4ba923dbac2845aca5b4312a5f278ad886a7acc7

SUMMARY = "XRes: X Resource extension library"

DESCRIPTION = "libXRes provides an X Window System client interface to \
the Resource extension to the X protocol.  The Resource extension allows \
for X clients to see and monitor the X resource usage of various clients \
(pixmaps, et al)."

require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libxres.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/${XORG_PN}-${PV}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=8c89441a8df261bdc56587465e13c7fa"

DEPENDS += "libxext xorgproto"

XORG_PN = "libXres"
