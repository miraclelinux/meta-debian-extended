# base recipe: meta-openembedded/meta-oe/recipes-support/daemonize/daemonize_git.bb
# base branch: warrior
# base commit: 7c25fa41fa40f74e36ff487c2ba0c857263093dd

SUMMARY = "A tool to run a command as a daemon"
HOMEPAGE = "http://software.clapper.org/daemonize/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=3cf9084faa88bc8554a9139d8d7dd35f"

inherit debian-package
require recipes-debian/sources/daemonize.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/daemonize-release-${PV}"
DEBIAN_PATCH_TYPE = "nopatch"
DEBIAN_QUILT_PATCHES = ""

inherit autotools

SRC_URI += "file://fix-ldflags-for-gnuhash.patch"

EXTRA_AUTORECONF += "--exclude=autoheader"
