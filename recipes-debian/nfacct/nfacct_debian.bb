#base recipe: openembedded/meta-openembedded/blob/master/meta-networking/recipes-filter/nfacct/nfacct_1.0.2.bb
#base branch: master
#base commit: 7c43284cd2754639582eae581a04f28558f42258

SUMMARY = "nfacct is the command line tool to create/retrieve/delete accounting objects"
HOMEPAGE = "http://netfilter.org/projects/nfacct/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

inherit debian-package
require recipes-debian/sources/nfacct.inc

DEBIAN_QUILT_PATCHES = ""

DEPENDS = "libnfnetlink libmnl libnetfilter-acct"

EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'

inherit autotools pkgconfig
