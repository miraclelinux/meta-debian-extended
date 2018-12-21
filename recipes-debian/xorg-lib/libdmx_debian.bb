require ${COREBASE}/meta/recipes-graphics/xorg-lib/xorg-lib-common.inc

SUMMARY = "DMX: Distributed Multihead X extension library"

DESCRIPTION = "The DMX extension provides support for communication with \
and control of Xdmx(1) server. Attributes of the Xdmx(1) server and of \
the back-end screens attached to the server can be queried and modified \
via this protocol."

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/libdmx.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_UNPACK_DIR = "${WORKDIR}/libdmx-${PV}"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=a3c3499231a8035efd0e004cfbd3b72a \
                    file://src/dmx.c;endline=33;md5=c43f19af03c7c8619cadc9724ed9afe1"

DEPENDS += "libxext xorgproto"
