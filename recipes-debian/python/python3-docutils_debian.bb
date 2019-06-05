# From: poky/meta/recipes-debian/python/python3-docutils_0.14.bb
# Rev: d3982592e143eacfe862d8a9653fd024e33c9714

SUMMARY = "Text processing system for documentation"
HOMEPAGE = "http://docutils.sourceforge.net"
SECTION = "devel/python"
LICENSE = "PSF & BSD-2-Clause & GPLv3"
LIC_FILES_CHKSUM = "file://COPYING.txt;md5=35a23d42b615470583563132872c97d6"

DEPENDS = "python3"

inherit debian-package
require recipes-debian/sources/python-docutils.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/docutils-${PV}"

inherit distutils3

BBCLASSEXTEND = "native"

