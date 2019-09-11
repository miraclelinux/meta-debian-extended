# base recipe: meta-networking/recipes-support/traceroute/traceroute_2.1.0.bb
# base branch: warrior
# base commit: 1cb19a31ba1e780cb3cfb3a37150d77281688c78
SUMMARY = "A new modern implementation of traceroute(8) utility for Linux systems"
DESCRIPTION = "The traceroute utility displays the route used by IP packets on \
their way to a specified network (or Internet) host.  Traceroute displays \
the IP number and host name (if possible) of the machines along the \
route taken by the packets.  Traceroute is used as a network debugging \
tool.  If you're having network connectivity problems, traceroute will \
show you where the trouble is coming from along the route."
SECTION = "net"
HOMEPAGE = "http://traceroute.sourceforge.net/"
LICENSE = "GPL-2.0+ & LGPL-2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

inherit debian-package
require recipes-debian/sources/traceroute.inc

inherit update-alternatives

SRC_URI += "file://filter-out-the-patches-from-subdirs.patch \
           "

EXTRA_OEMAKE = "VPATH=${STAGING_LIBDIR}"

do_compile() {
    export LDFLAGS="${TARGET_LDFLAGS} -L${S}/libsupp"
    oe_runmake "env=yes"
}

do_install() {
    install -d ${D}${bindir}
    install -m755 ${BPN}/${BPN} ${D}${bindir}

    install -m755 wrappers/tcptraceroute ${D}${bindir}

    install -d ${D}${mandir}/man8
    install -p -m644 ${BPN}/${BPN}.8 ${D}${mandir}/man8
    ln -s ${BPN}.8 ${D}${mandir}/man8/${BPN}6.8
    ln -s ${BPN}.8 ${D}${mandir}/man8/tcptraceroute.8

}

ALTERNATIVE_PRIORITY = "60"
ALTERNATIVE_${PN} = "traceroute"
ALTERNATIVE_LINK_NAME[traceroute] = "${bindir}/traceroute"
