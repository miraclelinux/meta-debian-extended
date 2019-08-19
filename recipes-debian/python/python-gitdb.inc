# base recipe: meta/recipes-devtools/python/python-gitdb.inc
# base branch: warrior
# base commit: 97e68f42bbfd8ec9bafab5c30f38cfb8f3dd5e3e

SUMMARY = "A pure-Python git object database"
HOMEPAGE = "http://github.com/gitpython-developers/gitdb"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=59e5ecb13339a936eedf83282eaf4528"

inherit pypi

PYPI_PACKAGE = "gitdb2"

inherit debian-package
require recipes-debian/sources/python-gitdb.inc

DEBIAN_QUILT_PATCHES = ""
DEBIAN_UNPACK_DIR = "${WORKDIR}/${PYPI_PACKAGE}-${PV}"

DEPENDS = "${PYTHON_PN}-async ${PYTHON_PN}-setuptools-native ${PYTHON_PN}-smmap"

RDEPENDS_${PN} += "${PYTHON_PN}-async \
                   ${PYTHON_PN}-compression \
                   ${PYTHON_PN}-crypt \
                   ${PYTHON_PN}-io \
                   ${PYTHON_PN}-mmap \
                   ${PYTHON_PN}-shell \
                   ${PYTHON_PN}-smmap \
"
BBCLASSEXTEND = "native nativesdk"
