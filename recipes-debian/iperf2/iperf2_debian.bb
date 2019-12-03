# base recipe: meta-openembedded/meta-oe/recipes-benchmark/iperf2/iperf2_2.0.10.bb
# base branch: warrior
# base commit: 196186de630c93529433d50292fd4e44e14af3f9

DESCRIPTION = "Iperf is a tool to measure maximum TCP bandwidth, allowing the tuning of various parameters and UDP characteristics"
HOMEPAGE = "https://sourceforge.net/projects/iperf2/"
SECTION = "console/network"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=e136a7b2560d80bcbf0d9b3e1356ecff"

inherit debian-package
require recipes-debian/sources/iperf.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/iperf-${PV}"

inherit autotools pkgconfig

EXTRA_OECONF = "--exec-prefix=${STAGING_DIR_HOST}${layout_exec_prefix}"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'ipv6', 'ipv6', '', d)}"
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"
