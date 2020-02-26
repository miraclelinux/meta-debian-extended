# base recipe: meta-rauc/recipes-support/zstd/zstd_git.bb
# base branch: warrior
# base commit: f6a5845f22c791e4ef33f09ecada1c51c020b597

SUMMARY = "Zstandard - Fast real-time compression algorithm"
HOMEPAGE = "http://www.zstd.net"
LICENSE = "BSD & GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c7f0b161edbe52f5f345a3d1311d0b32 \
                    file://contrib/linux-kernel/COPYING;md5=39bba7d2cf0ba1036f2a6e2be52fe3f0"

inherit debian-package
require recipes-debian/sources/libzstd.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/zstd-${PV}"

EXTRA_OECMAKE_append = " -DTHREADS_PTHREAD_ARG=0"

do_install() {
    oe_runmake DESTDIR=${D} install
}

BBCLASSEXTEND = "native nativesdk"
