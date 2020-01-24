# base recipe: recipes-support/libcheck/libcheck_0.12.0.bb
# base branch: warrior
# base commit: 21360dd971b39306b0332212a36ba9211f66e3a1

SUMMARY  = "Check - unit testing framework for C code"
HOMEPAGE = "https://libcheck.github.io/check/"
SECTION = "devel"

LICENSE  = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING.LESSER;md5=2d5025d4aa3495befef8f17206a5b0a1"

inherit debian-package
require recipes-debian/sources/check.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/check-${PV}"

FILESPATH_append = ":${COREBASE}/meta/recipes-support/libcheck/libcheck"
SRC_URI += "file://not-echo-compiler-info-to-check_stdint.h.patch \
            file://gawk.patch \
"

inherit autotools pkgconfig texinfo

CACHED_CONFIGUREVARS += "ac_cv_path_AWK_PATH=${bindir}/gawk"

RREPLACES_${PN} = "check (<= 0.9.5)"
RDEPENDS_${PN} += "gawk"
RDEPENDS_${PN}_class-native = ""

do_debian_patch_prepend() {
    cd ${DEBIAN_UNPACK_DIR}
    # remove patch for Debian
    sed -i -e '/01pkgconfig\.patch/d' ./debian/patches/series
}
