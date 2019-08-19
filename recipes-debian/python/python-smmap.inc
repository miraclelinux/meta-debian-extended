# base recipe: meta/recipes-debian/python/python-smmap.inc
# base branch: warrior
# base commit: 1b15b02b119221791f564ea94987336fbf00ca2c

SUMMARY = "Python implementation of a sliding window memory map manager"
DESCRIPTION = "A pure Python implementation of a sliding memory map to \
help unifying memory mapped access on 32 and 64 bit systems and to help \
managing resources more efficiently."
HOMEPAGE = "http://github.com/gitpython-developers/GitPython"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://PKG-INFO;beginline=8;endline=8;md5=e910b35b0ef4e1f665b9a75d6afb7709"

inherit pypi

PYPI_PACKAGE = "smmap2"

inherit debian-package
require recipes-debian/sources/python-smmap.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/${PYPI_PACKAGE}-${PV}"

RDEPENDS_${PN} += "${PYTHON_PN}-codecs \
                   ${PYTHON_PN}-mmap \
"
BBCLASSEXTEND = "native nativesdk"
