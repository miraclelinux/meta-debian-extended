# base recipe: meta/recipes-support/gmp/gmp_6.1.2.bb
# base branch: warrior
# base commit: cc8f888018f55e6227b846429186307000d76a27

require ${COREBASE}/meta/recipes-support/gmp/gmp.inc

LICENSE = "GPLv2+ | LGPLv3+"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                   file://COPYING.LESSERv3;md5=6a6a8e020838b23406c81b19c1d46df6 \
                   file://COPYINGv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
"

inherit debian-package
require recipes-debian/sources/gmp.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-support/gmp/gmp-6.1.2"
DEBIAN_UNPACK_DIR = "${WORKDIR}/gmp-${REPACK_PV}"

SRC_URI += " \
           file://amd64.patch \
           file://use-includedir.patch \
           file://0001-Append-the-user-provided-flags-to-the-auto-detected-.patch \
           file://0001-confiure.ac-Believe-the-cflags-from-environment.patch \
           "
acpaths = ""

EXTRA_OECONF += " --enable-cxx=detect"
EXTRA_OECONF_mipsarchr6_append = " --disable-assembly"

PACKAGES =+ "libgmpxx"
FILES_libgmpxx = "${libdir}/libgmpxx${SOLIBS}"

do_install_append() {
	oe_multilib_header gmp.h
}

do_install_prepend_class-target() {
        sed -i \
        -e "s|--sysroot=${STAGING_DIR_HOST}||g" \
        -e "s|${DEBUG_PREFIX_MAP}||g" \
         ${B}/gmp.h
}

SSTATE_SCAN_FILES += "gmp.h"

# Doesn't compile in MIPS16e mode due to use of hand-written
# assembly
MIPS_INSTRUCTION_SET = "mips"

BBCLASSEXTEND = "native nativesdk"
