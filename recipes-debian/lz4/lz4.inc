LICENSE = "BSD | BSD-2-Clause | GPL-2.0"
LIC_FILES_CHKSUM = "file://lib/LICENSE;md5=ebc2ea4814a64de7708f1571904b32cc \
                    file://programs/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://LICENSE;md5=d57c0d21cb917fb4e0af2454aa48b956 \
                    "

PE = "1"
PR = "0"

inherit debian-package
require recipes-debian/sources/lz4.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-support/lz4/files:"
SRC_URI += "file://run-ptest"

export CPPFLAGS = "-DLZ4F_PUBLISH_STATIC_FUNCTIONS"

EXTRA_OEMAKE = "PREFIX=${prefix} CC='${CC}' DESTDIR=${D} LIBDIR=${libdir} INCLUDEDIR=${includedir}"

do_install() {
        oe_runmake install
}

BBCLASSEXTEND = "native nativesdk"
