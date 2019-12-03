# base recipe: meta-openembedded/meta-python/recipes-devtools/python/python-psutil.inc
# base branch: warrior
# base commit: 77e5da1f24620aaf050d20b1fbfe00250c0fdda1

SUMMARY = "A cross-platform process and system utilities module for Python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f02e99f7f3c9a7fe8ecfc5d44c2be62"

inherit debian-package
require recipes-debian/sources/python-psutil.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/psutil-${PV}"

PACKAGES =+ "${PN}-tests"

FILES_${PN}-tests += " \
    ${PYTHON_SITEPACKAGES_DIR}/psutil/test* \
    ${PYTHON_SITEPACKAGES_DIR}/psutil/__pycache__/test* \
"


RDEPENDS_${PN} += " \
    ${PYTHON_PN}-shell \
    ${PYTHON_PN}-threading \
    ${PYTHON_PN}-xml \
"
