# base recipe: meta-oe/recipes-support/libestr/libestr_0.1.11.bb
# base branch: warrior
# base commit: 1a6f0053655db8657e4b6e710b16036397f1ad7a

SUMMARY = "some essentials for string handling (and a bit more)"
HOMEPAGE = "http://libestr.adiscon.com/"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=9d6c993486c18262afba4ca5bcb894d0"

inherit debian-package
require recipes-debian/sources/libestr.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-${PV}"
S = "${WORKDIR}/${BPN}-${PV}"

DEBIAN_QUILT_PATCHES = "" 

inherit autotools
