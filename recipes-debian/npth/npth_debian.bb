# base recipe: meta/recipes-support/npth/npth_1.6.bb
# base branch: warrior
# base commit: 38feebf501f683921e51e3f2888baa81cf6ee690

SUMMARY = "New GNU Portable Threads library"
HOMEPAGE = "http://www.gnupg.org/software/pth/"
SECTION = "libs"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=2caced0b25dfefd4c601d92bd15116de"
UPSTREAM_CHECK_URI = "https://gnupg.org/download/index.html"

inherit debian-package
require recipes-debian/sources/${BPN}.inc
DEBIAN_QUILT_PATCHES = ""

FILESPATH_append = ":${COREBASE}/meta/recipes-support/npth/npth"
SRC_URI += "file://pkgconfig.patch"

BINCONFIG = "${bindir}/npth-config"

inherit autotools binconfig-disabled multilib_header

FILES_${PN} = "${libdir}/libnpth.so.*"
FILES_${PN}-dev += "${bindir}/npth-config"

do_install_append() {
    oe_multilib_header npth.h
}

BBCLASSEXTEND = "native"
