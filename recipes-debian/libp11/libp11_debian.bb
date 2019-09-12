# base recipe: meta-openembedded/meta-oe/recipes-support/libp11/libp11_0.4.10.bb
# base branch: master
# base commit: e3875cdcf0e7fb1db3ebcdc1a7cadafb5b1803fa

SUMMARY = "Library for using PKCS"
DESCRIPTION = "\
Libp11 is a library implementing a small layer on top of PKCS \
make using PKCS"
HOMEPAGE = "https://github.com/OpenSC/libp11"
BUGTRACKER = "https://github.com/OpenSC/libp11/issues"
SECTION = "Development/Libraries"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=fad9b3332be894bab9bc501572864b29"
DEPENDS = "libtool openssl"

inherit debian-package
require recipes-debian/sources/libp11.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_QUILT_PATCHES = ""

inherit autotools pkgconfig

EXTRA_OECONF = "--disable-static"

do_install_append () {
    rm -rf ${D}${docdir}/${BPN}
}

FILES_${PN} += "${libdir}/engines*/pkcs11.so"
FILES_${PN}-dev += "${libdir}/engines*/libpkcs11${SOLIBSDEV}"
