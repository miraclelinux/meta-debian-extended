# base recipe: meta-python/recipes-devtools/python/python-webencodings.inc
# base branch: warrior
# base commit: 4fa2b14ee8067abc1f095cae49e32598e59e2e85

SUMMARY = "Character encoding aliases for legacy web content"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://PKG-INFO;beginline=8;endline=8;md5=e910b35b0ef4e1f665b9a75d6afb7709"

inherit pypi

inherit debian-package
require recipes-debian/sources/python-webencodings.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/webencodings-${PV}"
DEBIAN_QUILT_PATCHES = ""

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-codecs \
    ${PYTHON_PN}-json \
"

BBCLASSEXTEND = "native nativesdk"
