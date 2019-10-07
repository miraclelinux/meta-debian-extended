# base recipe: meta/recipes-connectivity/wireless-tools/wireless-tools_30.pre9.bb
# base branch: warrior
# base commit: f7137f94905c0d0ef1c0af8e53db18de00a17fec

SUMMARY = "Tools for the Linux Standard Wireless Extension Subsystem"
HOMEPAGE = "https://hewlettpackard.github.io/wireless-tools/Tools.html"
LICENSE = "GPLv2 & (LGPLv2.1 | MPL-1.1 | BSD)"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f \
                       file://iwconfig.c;beginline=1;endline=12;md5=cf710eb1795c376eb10ea4ff04649caf \
                       file://iwevent.c;beginline=59;endline=72;md5=d66a10026d4394f0a5b1c5587bce4537 \
                       file://sample_enc.c;beginline=1;endline=4;md5=838372be07874260b566bae2f6ed33b6"
SECTION = "base"
PE = "1"

inherit debian-package
require recipes-debian/sources/wireless-tools.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/wireless_tools.30"

SRC_URI += " \
           file://avoid_strip.patch \
           file://drop-symlink.patch \
          "

CFLAGS =+ "-I${S}"
EXTRA_OEMAKE = "-e 'BUILD_SHARED=y' \
               'INSTALL_DIR=${D}${base_sbindir}' \
               'INSTALL_LIB=${D}${libdir}' \
               'INSTALL_INC=${D}${includedir}' \
               'INSTALL_MAN=${D}${mandir}'"

do_compile() {
       oe_runmake all libiw.a
}

do_install() {
       oe_runmake PREFIX=${D} install-iwmulticall install-dynamic install-man install-hdr
       install -d ${D}${sbindir}
       install -m 0755 ifrename ${D}${sbindir}/ifrename

       #rm ${D}/usr/lib/x86_64-linux-gnu/libiw.so
}

PACKAGES = "libiw libiw-dev libiw-doc ifrename-doc ifrename ${PN} ${PN}-doc ${PN}-dbg"

FILES_libiw = "${libdir}/*.so.*"
FILES_libiw-dev = "${libdir}/*.a ${libdir}/*.so ${includedir}"
FILES_libiw-doc = "${mandir}/man7"
FILES_ifrename = "${sbindir}/ifrename"
FILES_ifrename-doc = "${mandir}/man8/ifrename.8 ${mandir}/man5/iftab.5"
FILES_${PN} = "${bindir} ${sbindir}/iw* ${base_sbindir} ${base_bindir} ${sysconfdir}/network"
FILES_${PN}-doc = "${mandir}"
