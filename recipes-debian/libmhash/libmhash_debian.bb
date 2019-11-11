# base recipe: meta-security/recipes-security/libmhash/libmhash_0.9.9.9.bb
# base branch: warrior
# base commit: 9b34cb18ca44fd17f0e54c404e96935adb91cdc7

SUMMARY = "Library of hashing algorithms."
DESCRIPTION = "\
  Mhash is a free (under GNU Lesser GPL) library \
  which provides a uniform interface to a large number of hash \
  algorithms. These algorithms can be used to compute checksums, \
  message digests, and other signatures. \
  "
HOMEPAGE = "http://mhash.sourceforge.net/"

inherit debian-package
require recipes-debian/sources/mhash.inc

LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=3bf50002aefd002f49e7bb854063f7e7"

SECTION = "libs"

SRC_URI += "file://Makefile.test \
    file://mhash.c \
    file://run-ptest \
    "

DEBIAN_UNPACK_DIR = "${WORKDIR}/mhash-${PV}.orig"

inherit autotools pkgconfig ptest

do_compile_prepend () {
    sed -i 's:-I$(top_builddir)/include/mutils:-I$(top_srcdir)/include:g' lib/Makefile
    sed -i 's:-I$(top_builddir)/include/mutils:-I$(top_srcdir)/include:g' src/Makefile
}

do_compile_ptest() {
    if [ ! -d ${S}/demo ]; then mkdir ${S}/demo; fi
    cp ${WORKDIR}/Makefile.test ${S}/demo/Makefile
    cp ${WORKDIR}/mhash.c ${S}/demo/
    cp include/mutils/mhash_config.h ${S}/include/mutils/
    cp -r lib/.libs ${S}/lib/
    make -C ${S}/demo CFLAGS="${CFLAGS} -I${S}/include/" LDFLAGS="${LDFLAGS} -L${S}/lib/.libs"
}

do_install_ptest() {
    install -m 0755 ${S}/demo/mhash ${D}${PTEST_PATH}
}
