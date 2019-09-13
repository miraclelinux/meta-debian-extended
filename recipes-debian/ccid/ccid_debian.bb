# base recipe: meta-openembedded/meta-oe/recipes-support/ccid/ccid_1.4.30.bb
# base branch: master
# base commit: 408050d37f9255425fb488a5651fe52ccae8cc33

SUMMARY = "Generic USB CCID smart card reader driver"
HOMEPAGE = "https://ccid.apdu.fr/"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=2d5025d4aa3495befef8f17206a5b0a1"

DEPENDS = "virtual/libusb0 pcsc-lite"
RDEPENDS_${PN} = "pcsc-lite"

inherit debian-package
require recipes-debian/sources/ccid.inc
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_QUILT_PATCHES = ""

SRC_URI += " \
    file://no-dep-on-libfl.patch \
"

inherit autotools pkgconfig

FILES_${PN} += "${libdir}/pcsc/"
FILES_${PN}-dbg += "${libdir}/pcsc/drivers/*/*/*/.debug"
