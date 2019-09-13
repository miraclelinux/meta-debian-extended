# base recipe: meta/recipes-support/lz4/lz4_1.8.3.bb
# base branch: warrior
# base commit: bb2d6cc85d558b2292a1a76d42c7cb1ff9713332

SUMMARY = "Extremely Fast Compression algorithm"
DESCRIPTION = "LZ4 is a very fast lossless compression algorithm, providing compression speed at 400 MB/s per core, scalable with multi-cores CPU. It also features an extremely fast decoder, with speed in multiple GB/s per core, typically reaching RAM speed limits on multi-core systems."

LICENSE = "BSD | BSD-2-Clause | GPL-2.0"
LIC_FILES_CHKSUM = "file://lib/LICENSE;md5=ebc2ea4814a64de7708f1571904b32cc \
                    file://programs/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://LICENSE;md5=d57c0d21cb917fb4e0af2454aa48b956 \
                    "

PE = "1"
PR = "0"

inherit debian-package
require recipes-debian/sources/lz4.inc

SRC_URI += "file://run-ptest"

export CPPFLAGS = "-DLZ4F_PUBLISH_STATIC_FUNCTIONS"

EXTRA_OEMAKE = "PREFIX=${prefix} CC='${CC}' DESTDIR=${D} LIBDIR=${libdir} INCLUDEDIR=${includedir}"

do_install() {
	oe_runmake install
}

BBCLASSEXTEND = "native nativesdk"

inherit ptest
RDEPENDS_${PN}-ptest += "make gcc"

do_compile_ptest() {
        cd ${S}/tests
        make
}

do_install_ptest() {
        install -d ${D}${PTEST_PATH}/testsuite
        cp -r --no-preserve=ownership ${S} ${D}${PTEST_PATH}/testsuite/lz4
}
