# base recipe: meta-openembedded/meta-networking/recipes-netkit/netkit-telnet/netkit-telnet_0.17.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

inherit debian-package
require recipes-debian/sources/netkit-telnet.inc

DESCRIPTION = "netkit-telnet includes the telnet daemon and client."
HOMEPAGE = "http://www.hcs.harvard.edu/~dholland/computers/netkit.html"
SECTION = "net"
DEPENDS = "ncurses"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://telnet/telnet.cc;beginline=2;endline=3;md5=780868e7b566313e70cb701560ca95ef"

SRC_URI += "\
           file://Warning-fix-in-the-step-of-install.patch \
           file://telnet-xinetd \
           file://cross-compile.patch \
           "

EXTRA_OEMAKE = "INSTALLROOT=${D} SBINDIR=${sbindir} DAEMONMODE=755 \
    MANMODE=644 MANDIR=${mandir}"

do_configure () {
    ./configure --prefix=${prefix}
    sed -e 's#^CFLAGS=\(.*\)$#CFLAGS= -D_GNU_SOURCE \1#' \
        -e 's#^CXXFLAGS=\(.*\)$#CXXFLAGS= -D_GNU_SOURCE \1#' \
        -e 's#^LDFLAGS=.*$#LDFLAGS= ${LDFLAGS}#' \
        -i MCONFIG
}

do_compile () {
    oe_runmake 'CC=${CC}' 'LD=${LD}' 'LDFLAGS=${LDFLAGS}' SUB=telnet
    oe_runmake 'CC=${CC}' 'LD=${LD}' 'LDFLAGS=${LDFLAGS}' LIBS=-lutil SUB=telnetd
    oe_runmake 'CC=${CC}' 'LD=${LD}' 'LDFLAGS=${LDFLAGS}' SUB=telnetlogin
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 telnet/telnet ${D}${bindir}/telnet.${PN}
    install -d ${D}${sbindir}
    install -d ${D}${mandir}/man1
    install -d ${D}${mandir}/man5
    install -d ${D}${mandir}/man8
    oe_runmake SUB=telnetd install
    rm -rf ${D}${mandir}/man1
    install -D -m 4750 ${B}/telnetlogin/telnetlogin ${D}/${libdir}/telnetlogin
    # fix up hardcoded paths
    sed -i -e 's,/usr/sbin/,${sbindir}/,' ${WORKDIR}/telnet-xinetd
    install -d  ${D}/etc/xinetd.d/
    install -p -m644 ${WORKDIR}/telnet-xinetd ${D}/etc/xinetd.d/telnet
}

inherit update-alternatives

ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE_${PN} = "telnet"
ALTERNATIVE_LINK_NAME[telnet] = "${bindir}/telnet"
ALTERNATIVE_TARGET[telnet] = "${bindir}/telnet.${PN}"

FILES_${PN} += "${sbindir}/in.* ${libdir}/* ${sysconfdir}/xinetd.d/*"

# http://errors.yoctoproject.org/Errors/Details/186954/
EXCLUDE_FROM_WORLD_libc-musl = "1"
RCONFLICTS_${PN} = "inetutils-telnetd"
