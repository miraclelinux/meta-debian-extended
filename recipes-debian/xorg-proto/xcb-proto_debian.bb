# base recipe: meta/recipes-graphics/xorg-proto/xcb-proto_1.13.bb
# base branch: warrior
# base commit: 5ce58802e03fa8c32212d85f99d57aeabf8d3b53

SUMMARY = "XCB: The X protocol C binding headers"
DESCRIPTION = "Function prototypes for the X protocol C-language Binding \
(XCB).  XCB is a replacement for Xlib featuring a small footprint, \
latency hiding, direct access to the protocol, improved threading \
support, and extensibility."
HOMEPAGE = "http://xcb.freedesktop.org"
BUGTRACKER = "https://bugs.freedesktop.org/enter_bug.cgi?product=XCB"

SECTION = "x11/libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=d763b081cb10c223435b01e00dc0aba7 \
                    file://src/dri2.xml;beginline=2;endline=28;md5=f8763b13ff432e8597e0d610cf598e65"

inherit debian-package
require recipes-debian/sources/xcb-proto.inc
DEBIAN_PATCH_TYPE = "nopatch"

inherit autotools pkgconfig python3native

PACKAGES += "python-xcbgen"

FILES_${PN} = ""
FILES_${PN}-dev += "${datadir}/xcb/*.xml ${datadir}/xcb/*.xsd"
FILES_python-xcbgen = "${PYTHON_SITEPACKAGES_DIR}"

RDEPENDS_${PN}-dev = ""
RRECOMMENDS_${PN}-dbg = "${PN}-dev (= ${EXTENDPKGV})"

BBCLASSEXTEND = "native nativesdk"
