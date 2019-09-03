# base recipe: poky/meta/recipes-extended/libaio/libaio_0.3.111.bb
# base branch: warrior
# base commit: 6b7227b68a7ecdf20829e555440a9cfed14db176

SUMMARY = "Asynchronous I/O library"
DESCRIPTION = "Asynchronous input/output library that uses the kernels native interface"
HOMEPAGE = "http://lse.sourceforge.net/io/aio.html"

LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d8045f3b8f929c1cb29a1e3fd737b499"

inherit debian-package
require recipes-debian/sources/libaio.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-${PV}"

FILESPATH_append = ":${COREBASE}/meta/recipes-extended/libaio/libaio/"

SRC_URI += " \
           file://00_arches.patch \
           file://libaio_fix_for_mips_syscalls.patch \
           "

S = "${WORKDIR}/${BPN}-${PV}"

EXTRA_OEMAKE =+ "prefix=${prefix} includedir=${includedir} libdir=${libdir}"

do_install () {
    oe_runmake install DESTDIR=${D}
}

BBCLASSEXTEND = "native nativesdk"
