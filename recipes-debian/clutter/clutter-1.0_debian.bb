# base recipe: meta/recipes-graphics/clutter/clutter-1.0_1.26.2.bb
# base branch: warrior
# base commit: 56b56aef129c1753f1a5bfd6e5b8451bfd0f242d

require ${COREBASE}/meta/recipes-graphics/clutter/clutter-1.0.inc

inherit debian-package
require recipes-debian/sources/${BPN}.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/clutter-${PV}"

LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

FILESPATH_append = ":${COREBASE}/meta/recipes-graphics/clutter/clutter-1.0"
SRC_URI += "\
	file://install-examples.patch \
	file://run-installed-tests-with-tap-output.patch \
	file://0001-Remove-clutter.types-as-it-is-build-configuration-sp.patch \
	file://run-ptest \
"
