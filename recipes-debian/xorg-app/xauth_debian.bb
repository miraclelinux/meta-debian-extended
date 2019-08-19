# base recipe: meta/recipes-graphics/xorg-app/xauth_1.0.10.bb
# base branch: warrior
# base commit: 254bfb107134702d8d1e0bfbdd1b011212e8c291

require ${COREBASE}/meta/recipes-graphics/xorg-app/xorg-app-common.inc

# clear SRC_URI
SRC_URI = ""
inherit debian-package
require recipes-debian/sources/xauth.inc
DEBIAN_PATCH_TYPE = "nopatch"

SUMMARY = "X authority utilities"
DESCRIPTION = "X application to edit and display the authorization \
information used in connecting to the X server."

LIC_FILES_CHKSUM = "file://COPYING;md5=5ec74dd7ea4d10c4715a7c44f159a40b"

DEPENDS += "libxau libxext libxmu"
PE = "1"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)}"
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"
