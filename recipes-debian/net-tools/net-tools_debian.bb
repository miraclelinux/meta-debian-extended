# base recipe: poky/meta/recipes-extended/net-tools/net-tools_1.60-26.bb
# base branch: warrior
# base commit: fec5323dba7e23a42073995020c7336f3e6a7de1

SUMMARY = "Basic networking tools"
DESCRIPTION = "A collection of programs that form the base set of the NET-3 networking distribution for the Linux operating system"
HOMEPAGE = "http://net-tools.berlios.de/"
BUGTRACKER = "http://bugs.debian.org/net-tools"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://ifconfig.c;beginline=11;endline=15;md5=d1ca372080ad5401e23ca0afc35cf9ba"

inherit debian-package
require recipes-debian/sources/net-tools.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-extended/net-tools/net-tools"
SRC_URI += "file://net-tools-config.h \
            file://net-tools-config.make \
            file://musl-fixes.patch \
            file://net-tools-1.60-sctp1.patch \
           "

inherit gettext

do_patch[depends] += "quilt-native:do_populate_sysroot"

# The Makefile is lame, no parallel build
PARALLEL_MAKE = ""

do_unpack[cleandirs] += "${S}"

do_configure() {
	# net-tools has its own config mechanism requiring "make config"
	# we pre-generate desired options and copy to source directory instead
	cp ${WORKDIR}/net-tools-config.h    ${S}/config.h
	cp ${WORKDIR}/net-tools-config.make ${S}/config.make

        echo 'HAVE_ARP_TOOLS=1' >> ${S}/config.make
        echo 'HAVE_HOSTNAME_TOOLS=1' >> ${S}/config.make
        echo 'HAVE_HOSTNAME_SYMLINKS=1' >> ${S}/config.make
        echo 'HAVE_PLIP_TOOLS=1' >> ${S}/config.make
        echo 'HAVE_SERIAL_TOOLS=1' >> ${S}/config.make

	if [ "${USE_NLS}" = "no" ]; then
		sed -i -e 's/^I18N=1/# I18N=1/' ${S}/config.make
	fi
}

do_compile() {
	# net-tools use COPTS/LOPTS to allow adding custom options
	oe_runmake COPTS="$CFLAGS" LOPTS="$LDFLAGS"
}

do_install() {
	# We don't need COPTS or LOPTS, but let's be consistent.
	oe_runmake COPTS="$CFLAGS" LOPTS="$LDFLAGS" 'BASEDIR=${D}' install

	if [ "${base_bindir}" != "/bin" ]; then
		mkdir -p ${D}/${base_bindir}
		mv ${D}/bin/* ${D}/${base_bindir}/
		rmdir ${D}/bin
	fi
	if [ "${base_sbindir}" != "/sbin" ]; then
		mkdir ${D}/${base_sbindir}
		mv ${D}/sbin/* ${D}/${base_sbindir}/
		rmdir ${D}/sbin
	fi
}

inherit update-alternatives

base_sbindir_progs = "arp ipmaddr iptunnel mii-tool nameif plipconfig rarp slattach"
base_bindir_progs  = "dnsdomainname domainname hostname ifconfig nisdomainname route netstat ypdomainname"

ALTERNATIVE_${PN} = "${base_sbindir_progs} ${base_bindir_progs}"
ALTERNATIVE_${PN}-doc += "hostname.1 dnsdomainname.1"
ALTERNATIVE_LINK_NAME[hostname.1] = "${mandir}/man1/hostname.1"
ALTERNATIVE_LINK_NAME[dnsdomainname.1] = "${mandir}/man1/dnsdomainname.1"
ALTERNATIVE_PRIORITY[hostname.1] = "10"

python __anonymous() {
    for prog in d.getVar('base_sbindir_progs').split():
        d.setVarFlag('ALTERNATIVE_LINK_NAME', prog, '%s/%s' % (d.getVar('base_sbindir'), prog))
    for prog in d.getVar('base_bindir_progs').split():
        d.setVarFlag('ALTERNATIVE_LINK_NAME', prog, '%s/%s' % (d.getVar('base_bindir'), prog))
}
ALTERNATIVE_PRIORITY = "100"

BBCLASSEXTEND = "native nativesdk"
