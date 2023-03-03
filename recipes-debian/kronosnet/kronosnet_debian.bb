#base recipe: meta-openembedded/meta-networking/recipes-extended/kronosnet/kronosnet_1.15.bb
#base branch: dunfell
#base commit: 6792ebdd966aa0fb662989529193a0940fbfee00

SUMMARY = " Kronosnet, often referred to as knet, is a network abstraction layer designed for High Availability use cases, where redundancy, security, fault tolerance and fast fail-over are the core requirements of your application."
HOMEPAGE = "https://kronosnet.org/"
LICENSE = "GPL-2.0+ & LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING.applications;md5=751419260aa954499f7abaabaa882bbe \
                    file://COPYING.libraries;md5=2d5025d4aa3495befef8f17206a5b0a1"
SECTION = "libs"
DEPENDS = "doxygen-native libqb-native libxml2-native bzip2 libqb libxml2 libnl libsctp lz4 lzo openssl nss xz zlib"

inherit autotools debian-package
require recipes-debian/sources/kronosnet.inc

# Unset the cache of ac_cv_header_netinet_sctp_h to check the existence of netinet/sctp.h.
SRC_URI += "file://configure.ac.patch"

# libknet/transport_udp.c:326:48: error: comparison of integers of different signs: 'unsigned long' and 'int' [-Werror,-Wsign-compare]
# for (cmsg = CMSG_FIRSTHDR(&msg);cmsg; cmsg = CMSG_NXTHDR(&msg, cmsg)) {
#                                                             ^~~~~~~~~~~~~~~~~~~~~~~
CFLAGS_append_toolchain-clang = " -Wno-sign-compare"

PACKAGES =+ "libnozzle libnozzle-dev libknet libknet-dev"

FILES_libnozzle = "${libdir}/libnozzle.so.*"

FILES_libnozzle-dev = " \
    ${includedir}/libnozzle.h \
    ${libdir}/libnozzle.so \
    ${libdir}/pkgconfig/libnozzle.pc \
    "

FILES_libknet = " \
    ${libdir}/kronosnet/*.so \
    ${libdir}/libknet.so.* \
    "

FILES_libknet-dev = " \
    ${includedir}/libknet.h \
    ${libdir}/libknet.so \
    ${libdir}/pkgconfig/libknet.pc \
    "
