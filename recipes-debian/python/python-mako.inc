# base recipe: meta/recipes-devtools/python/python-mako.inc
# base branch: warrior
# base commit: 23626bd9025aad8a10d7cb13796a493e9db816e1

SUMMARY = "Templating library for Python"
HOMEPAGE = "http://www.makotemplates.org/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1bb21fa2d2f7a534c884b990430a6863"

PYPI_PACKAGE = "Mako"

inherit pypi

DEBIAN_UNPACK_DIR="${WORKDIR}/${PYPI_PACKAGE}-${PV}"

inherit debian-package
require recipes-debian/sources/mako.inc

RDEPENDS_${PN} = "${PYTHON_PN}-html \
                  ${PYTHON_PN}-netclient \
                  ${PYTHON_PN}-threading \
"

RDEPENDS_${PN}_class-native = ""

BBCLASSEXTEND = "native nativesdk"
