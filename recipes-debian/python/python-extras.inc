# From: poky/meta/recipes-devtools/python/python-extras.inc
# Rev: 76fbee59374d282c9d7e62db95c0968745411777
SUMMARY = "Useful extra bits for Python - things that should be in the standard library"
HOMEPAGE = "https://pypi.python.org/pypi/extras/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6d108f338b2f68fe48ac366c4650bd8b"

inherit pypi

inherit debian-package
require recipes-debian/sources/python-extras.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/python-extras-${PV}"
DEBIAN_QUILT_PATCHES = ""

BBCLASSEXTEND = "nativesdk"

