#base recipe: meta-networking/recipes-support/lksctp-tools/lksctp-tools_1.0.18.bb
#base branch: dunfell
#base commit: 6792ebdd966aa0fb662989529193a0940fbfee00

SUMMARY = "The Linux Kernel Stream Control Transmission Protocol (lksctp) project"
HOMEPAGE = "http://lksctp.org"
SECTION = "net"
LICENSE = "LGPLv2.1 & GPLv2 & BSD-3-Clause"

LIC_FILES_CHKSUM = " \
    file://COPYING.lib;md5=0a1b79af951c42a9c8573533fbba9a92 \
    file://COPYING;md5=0c56db0143f4f80c369ee3af7425af6e \
    file://debian/copyright;md5=8994a5c1fee3d056ab38bececf16e2c3 \
"

LK_REL = "1.0.18"

inherit debian-package
require recipes-debian/sources/lksctp-tools.inc

BPN = "lksctp-tools"

SRC_URI += " \
    file://0001-build-remove-v4.12-secondary-defines-in-favor-of-HAV.patch \
    file://0001-build-fix-probing-for-HAVE_SCTP_SENDV.patch \
    file://0001-build-0b0dce7a36fb-actually-belongs-to-v4.19.patch \
    file://0001-test_1_to_1_events.c-initialize-event-properly.patch \
"

BBCLASSEXTEND = "native"

inherit autotools-brokensep pkgconfig binconfig

do_install () {
    install -d ${D}${libdir}/pkgconfig
    cp -r --no-preserve=ownership ${S}/src/lib/.libs/libsctp${SOLIBS} ${D}${libdir}
    cp -r --no-preserve=ownership ${S}/src/lib/.libs/libsctp${SOLIBSDEV} ${D}${libdir}
    install -m 644 ${S}/libsctp.pc ${D}${libdir}/pkgconfig

    install -d ${D}${includedir}/netinet
    install -m 644 ${S}/src/include/netinet/sctp.h ${D}${includedir}/netinet

    install -d ${D}${docdir}/libsctp-dev/examples
    install -m 644 ${S}/src/withsctp/*.c ${D}${docdir}/libsctp-dev/examples
    install -m 644 ${S}/src/withsctp/*.h ${D}${docdir}/libsctp-dev/examples
    install -m 644 ${S}/src/apps/sctp_*.c ${D}${docdir}/libsctp-dev/examples
    install -m 644 ${S}/src/apps/sctp_*.h ${D}${docdir}/libsctp-dev/examples
}

SOLIBVERSION="${LK_REL}"
SOLIBMAJORVERSION="1"

FILES_${PN} = " \
    ${libdir}/libsctp.so.${SOLIBVERSION} \
    ${libdir}/libsctp.so.${SOLIBMAJORVERSION} \
"

FILES_${PN}-dev += " \
    ${libdir}/pkgconfig/libsctp.pc \
    ${libdir}/libsctp.so \
    ${docdir}/libsctp-dev/examples/* \
    ${includedir}/netinet/sctp.h \
"

RRECOMMENDS_${PN} += "kernel-module-sctp"
