# base recipe: meta-openembedded/meta-networking/recipes-netkit/netkit-telnet/netkit-ftp_0.17.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

inherit debian-package
require recipes-debian/sources/netkit-ftp.inc

DESCRIPTION = "netkit-ft includes the ftp client."
SECTION = "net"
HOMEPAGE="ftp://ftp.uk.linux.org/pub/linux/Networking/netkit"
LICENSE = "BSD"

LIC_FILES_CHKSUM = "file://ftp/ftp.c;beginline=2;endline=3;md5=2d40a75a50d83b8f6317b3f53db72bfa"

SRC_URI += " \
           file://Add_ARG_MAX_define.patch \
           file://0001-ftp-include-sys-types.h-for-u_long.patch \
           "

inherit autotools-brokensep

do_configure () {
    ./configure --prefix=${prefix}
    echo "LDFLAGS=${LDFLAGS}" >> MCONFIG
}

BINMODE = "0755"
MANMODE = "0644"

do_install () {
    install -d ${D}${bindir}
    install -d ${D}${mandir}/man1
    install -d ${D}${mandir}/man5

    install -m${BINMODE} ${S}/ftp/ftp ${D}${bindir}
    ln -sf ftp ${D}${bindir}/pftp
    install -m${MANMODE} ${S}/ftp/ftp.1 ${D}${mandir}/man1
    ln -sf ftp.1 ${D}${mandir}/man1/pftp.1
    install -m${MANMODE} ${S}/ftp/netrc.5 ${D}${mandir}/man5
}

PACKAGES = "${PN} ${PN}-doc ${BPN}-dbg"
FILES_${PN} = "${bindir}/*"
FILES_${PN}-doc = "${mandir}"
FILES_${PN}-dbg = "${prefix}/src/debug \
            ${bindir}/.debug"

RDEPENDS_${PN} = "readline"
