# base recipe: meta-python/recipes-devtools/python/python-html5lib.inc
# base branch: warrior
# base commit: 8551d233d1e94ae295d85677c9306d43aeb752ef

SUMMARY = "HTML parser based on the WHATWG HTML specifcation"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ba5ada9e6fead1fdc32f43c9f10ba7c"

inherit pypi

inherit debian-package
require recipes-debian/sources/html5lib.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/html5lib-${PV}"
DEBIAN_QUILT_PATCHES = ""

RDEPENDS_${PN} += "\
    ${PYTHON_PN}-lxml \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-webencodings \
    ${PYTHON_PN}-xml \
"

BBCLASSEXTEND = "native nativesdk"
