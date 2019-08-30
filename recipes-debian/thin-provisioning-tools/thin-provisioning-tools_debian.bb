# base recipe: https://github.com/openembedded/meta-openembedded/tree/warrior/meta-oe/recipes-support/thin-provisioning-tools/thin-provisioning-tools_0.7.6.bb
# base branch: warrior
# base commit: 882d907f77e21df644ec7c5bc910ea13e915f600


SUMMARY = "Tools of dm-thin device-mapper"
DESCRIPTION = "A suite of tools for manipulating the metadata of the dm-thin device-mapper target."
HOMEPAGE = "https://github.com/jthornber/thin-provisioning-tools"
LICENSE = "GPLv3"
SECTION = "devel"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

inherit debian-package
require recipes-debian/sources/thin-provisioning-tools.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}-${PV}"
S = "${WORKDIR}/${BPN}-${PV}"

SRC_URI += " \
           file://0001-fix-compile-failed-with-libc-musl.patch \
           file://use-sh-on-path.patch \
"

UPSTREAM_CHECK_GITTAGREGEX = "v(?P<pver>\d+(\.\d+)+)"

DEPENDS += "expat libaio boost"

inherit autotools-brokensep
