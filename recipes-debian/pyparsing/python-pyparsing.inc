# base recipe: meta/recipes-devtools/python/python-pyparsing.inc
# base branch: master
# base commit: 79821d5a185e25384f5b6b5158b238bbee17c79e

SUMMARY = "Python parsing module"
HOMEPAGE = "http://pyparsing.wikispaces.com/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fb46329938e6bc829b256e37d5c1e31a"

inherit debian-package
require recipes-debian/sources/pyparsing.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/pyparsing-${REPACK_PV}"
S = "${WORKDIR}/pyparsing-${REPACK_PV}"

DEBIAN_PATCH_TYPE = "quilt"
DEBIAN_QUILT_PATCHES = ""

UPSTREAM_CHECK_REGEX = "pyparsing-(?P<pver>.*)\.tar"

RDEPENDS_${PN} += " \
    ${PYTHON_PN}-datetime \
    ${PYTHON_PN}-debugger \
    ${PYTHON_PN}-json \
    ${PYTHON_PN}-netclient \
    ${PYTHON_PN}-pprint \
    ${PYTHON_PN}-stringold \
    ${PYTHON_PN}-threading \
"

BBCLASSEXTEND = "native nativesdk"
