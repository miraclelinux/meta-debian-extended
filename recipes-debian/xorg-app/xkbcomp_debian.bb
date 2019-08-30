# base recipe: meta/recipes-graphics/xorg-app/xkbcomp_1.4.2.bb
# base branch: warrior
# base commit: 4623554463685000907eb3e17b4b23136f3759a2

SUMMARY = "A program to compile XKB keyboard description"

DESCRIPTION = "The xkbcomp keymap compiler converts a description of an \
XKB keymap into one of several output formats. The most common use for \
xkbcomp is to create a compiled keymap file (.xkm extension) which can \
be read directly by XKB-capable X servers or utilities."

HOMEPAGE = "http://www.x.org/"
BUGTRACKER = "https://bugs.freedesktop.org"
SECTION = "x11/apps"
LICENSE = "MIT-X"
DEPENDS = "util-macros-native virtual/libx11"

# depends on virtual/libx11
REQUIRED_DISTRO_FEATURES = "x11"

inherit debian-package
require recipes-debian/sources/x11-xkb-utils.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/x11-xkb-utils.git"
S = "${DEBIAN_UNPACK_DIR}/xkbcomp"
DEBIAN_PATCH_TYPE = "quilt"

# x11-xkb-utils is a collection of sub-packages, and
# each sub-package has its own version.
PV = "1.4.0"

inherit autotools pkgconfig distro_features_check

FILES_${PN} += " ${libdir}/X11/${BPN} ${datadir}/X11/app-defaults/"

LIC_FILES_CHKSUM = "file://COPYING;md5=08436e4f4476964e2e2dd7e7e41e076a"

DEPENDS += "libxkbfile"

BBCLASSEXTEND = "native"
