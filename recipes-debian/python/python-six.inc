# From: poky/meta/recipes-devtools/python/python-six.inc
# REv: aadf0716edbad9002173800e15965eda33796ad5
SUMMARY = "Python 2 and 3 compatibility library"
HOMEPAGE = "https://pypi.python.org/pypi/six/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=83e0f622bd5ac7d575dbd83d094d69b5"

inherit pypi

inherit debian-package
require recipes-debian/sources/six.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/six-${PV}"
DEBIAN_QUILT_PATCHES = ""

RDEPENDS_${PN} = "${PYTHON_PN}-io"

BBCLASSEXTEND = "native nativesdk"
