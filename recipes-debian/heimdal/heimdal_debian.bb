# base recipe: meta-debian/recipes-debian/heimdal/heimdal_debian.bb
# base branch: warrior
# base commit: 3f609957cd1c373ced7ad32d21e5d688f118eba0
Description="Heimdal is a Kerberos 5 implementation."

inherit debian-package autotools-brokensep binconfig
require recipes-debian/sources/heimdal.inc

LICENSE = "PD & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2580e0b4d85feafcd5c339f5be3c8229"

DEPENDS += " bison-native flex-native heimdal-native"
DEPENDS += " db e2fsprogs"

EXTRA_OECONF += "\
	--libexecdir="${sbindir}" \
	--prefix="${prefix}" \
	--enable-kcm \
	--without-openssl \
	--infodir="${datadir}/info" \
	--datarootdir="${datadir}" \
	--libdir="${libdir}" \
	--without-krb4 \
	--with-cross-tools=${STAGING_BINDIR_NATIVE} \
	ac_cv_func_getpwnam_r_posix=yes \
"

do_compile_prepend() {
	cp ${S}/kdc/kdc-protos.h  ${S}/include/kdc-protos.h
	cp ${S}/kdc/kdc-private.h ${S}/include/kdc-private.h
}

do_install_prepend() {
	cp ${S}/include/kdc-protos.h  ${S}/kdc/kdc-protos.h
	cp ${S}/include/kdc-private.h ${S}/kdc/kdc-private.h
}

do_install_append() {
	# Remove unwanted files
	rm -f ${D}${libdir}/windc.a
	rm -f ${D}${libdir}/windc.so*

	install -d ${D}${datadir}/heimdal-kdc
	cp ${S}/debian/extras/kdc.conf ${D}${datadir}/heimdal-kdc
	cp ${S}/debian/extras/kadmind.acl ${D}${datadir}/heimdal-kdc

	mkdir -p ${D}${infodir}
	mkdir -p ${D}${libdir}/${BPN}/pkgconfig

	mv ${D}${libdir}/*.so ${D}${libdir}/${BPN}
	for file in ${D}${libdir}/${BPN}/*.so; do
		ln -sf ../$(basename $(readlink $file)) $file
	done

	# remove general purpose utilities
	rm -rf ${D}${bindir}/bsearch
	rm -rf ${D}${mandir}/cat1/bsearch.1
	rm -rf ${D}${mandir}/man1/bsearch.1

	rm -rf ${D}${bindir}/idn-lookup
	rm -rf ${D}${mandir}/man1/idn-lookup.1

	cp ${D}${bindir}/krb5-config ${D}${bindir}/krb5-config.heimdal
	cp ${D}${mandir}/man1/krb5-config.1 ${D}${mandir}/man1/krb5-config.heimdal.1
	mkdir -p ${D}${sysconfdir}/default
	mkdir -p ${D}${sysconfdir}/ldap/schema
	install -m 0644 ${S}/debian/extras/default ${D}${sysconfdir}/default/heimdal-kdc
	install -m 0644 ${S}/lib/hdb/hdb.schema ${D}${sysconfdir}/ldap/schema/hdb.schema

	# Rename k* user commands
	mv ${D}${bindir}/kadmin ${D}${bindir}/kadmin.heimdal
	mv ${D}${mandir}/man1/kadmin.1 ${D}${mandir}/man1/kadmin.heimdal.1

 	mv ${D}${bindir}/kdestroy ${D}${bindir}/kdestroy.heimdal
	mv ${D}${mandir}/man1/kdestroy.1 ${D}${mandir}/man1/kdestroy.heimdal.1

	mv ${D}${bindir}/kinit ${D}${bindir}/kinit.heimdal
	mv ${D}${mandir}/man1/kinit.1 ${D}${mandir}/man1/kinit.heimdal.1

	mv ${D}${bindir}/klist ${D}${bindir}/klist.heimdal
	mv ${D}${mandir}/man1/klist.1 ${D}${mandir}/man1/klist.heimdal.1

	mv ${D}${bindir}/pagsh ${D}${bindir}/kpagsh
	mv ${D}${mandir}/man1/pagsh.1 ${D}${mandir}/man1/kpagsh.1

	mv ${D}${bindir}/kpasswd ${D}${bindir}/kpasswd.heimdal
	mv ${D}${mandir}/man1/kpasswd.1 ${D}${mandir}/man1/kpasswd.heimdal.1

	mv ${D}${bindir}/su ${D}${bindir}/ksu
	mv ${D}${mandir}/man1/su.1 ${D}${mandir}/man1/ksu.1
	mv ${D}${bindir}/ksu ${D}${bindir}/ksu.heimdal
	mv ${D}${mandir}/man1/ksu.1 ${D}${mandir}/man1/ksu.heimdal.1

	mv ${D}${bindir}/ktutil ${D}${bindir}/ktutil.heimdal
	mv ${D}${mandir}/man1/ktutil.1 ${D}${mandir}/man1/ktutil.heimdal.1

	mv ${D}${bindir}/kswitch ${D}${bindir}/kswitch.heimdal
	mv ${D}${mandir}/man1/kswitch.1 ${D}${mandir}/man1/kswitch.heimdal.1

	mv ${D}${mandir}/man5/krb5.conf.5 ${D}${mandir}/man5/krb5.conf.5heimdal

	# remove conflicting files
	rm -rf debian/heimdal-dev/usr/include/ss
	rm -f debian/heimdal-dev/usr/bin/mk_cmds
	rm -f debian/heimdal-dev/usr/include/fnmatch.h
	# remove unwanted files
	rm -f debian/heimdal-dev/usr/lib/libss.a
	rm -f debian/heimdal-dev/usr/lib/libss.la
	rm -f debian/heimdal-dev/usr/lib/libss.so
	rm -f debian/heimdal-dev/usr/lib/windc.la
	# remove libtool recursive linking mess
	sed -i "/dependency_libs/ s/'.*'/''/" ${D}${libdir}/*.la
	sed -i "s/libdir=.*/libdir=\/usr\/lib\/heimdal/" \
		${D}${libdir}/pkgconfig/*.pc
	sed -i "s/includedir=.*/includedir=\/usr\/include\/heimdal/" \
		${D}${libdir}/pkgconfig/*.pc

	cp ${D}${libdir}/pkgconfig/krb5*.pc ${D}${libdir}/${BPN}/pkgconfig
	cp ${D}${libdir}/pkgconfig/kadm*.pc ${D}${libdir}/${BPN}/pkgconfig

	# Install init script
	install -d ${D}${sysconfdir}/init.d
	install -m 0644 ${S}/debian/heimdal-kcm.init \
			${D}${sysconfdir}/init.d/heimdal-kcm
	install -m 0644 ${S}/debian/heimdal-kdc.init \
			${D}${sysconfdir}/init.d/heimdal-kdc

	install -d ${D}${sysconfdir}/logrotate.d
	install -m 0644 ${S}/debian/heimdal-kdc.logrotate \
			${D}${sysconfdir}/logrotate.d/heimdal-kdc

	# install heimdal-kdc package
	install -d ${D}${libdir}/${BPN}-servers
	mv ${D}${sbindir}/kadmind ${D}${libdir}/${BPN}-servers
	mv ${D}${sbindir}/kdc ${D}${libdir}/${BPN}-servers
	mv ${D}${sbindir}/kpasswdd ${D}${libdir}/${BPN}-servers

	# install heimdal-multidev package
	mv ${D}${sbindir}/${BPN}/asn1_compile ${D}${bindir}
	mv ${D}${sbindir}/${BPN}/asn1_print ${D}${bindir}
	mv ${D}${sbindir}/${BPN}/slc ${D}${bindir}
	rm -rf ${D}${sbindir}/${BPN}
	install -d ${D}${includedir}/${BPN}
	cd ${D}${includedir}
	mv `ls --hide=${BPN}` ${BPN}
	cd -

	# install heimdal-servers package
	install -d ${D}${libdir}/${BPN}-servers
	mv ${D}${sbindir}/kfd ${D}${libdir}/${BPN}-servers
}

PACKAGES  =+ "${BPN}-servers ${BPN}-multidev \
		${BPN}-clients ${BPN}-kdc ${BPN}-kcm"

#Overwrite default PACKAGES(-dev), because of heimdal provide package name conflict default PACKAGES name from bitbake.conf
FILES_${PN} = "${libdir}/*.so.*"
FILES_${PN}-dev = "\
		${bindir}/krb5-config \
		${libdir}/pkgconfig/kadm-client.pc \
		${libdir}/pkgconfig/kadm-server.pc \
		${libdir}/pkgconfig/krb5.pc \
		${libdir}/pkgconfig/krb5-gssapi.pc"

FILES_${BPN}-clients += "\
	${bindir}/* \
	${sbindir}/kdigest ${sbindir}/kimpersonate"

FILES_${BPN}-kcm += "\
	${sysconfdir}/init.d/heimdal-kcm \
	${sbindir}/kcm"

FILES_${BPN}-kdc += "\
	${datadir}/${BPN}-kdc/* \
	${sbindir}/digest-service ${sbindir}/hprop \
	${sbindir}/hpropd ${sbindir}/iprop-log \
	${sbindir}/ipropd-master ${sbindir}/ipropd-slave \
	${sbindir}/kstash ${libdir}/${BPN}-servers/kadmind \
	${libdir}/${BPN}-servers/kdc ${libdir}/${BPN}-servers/kpasswdd \
	${sysconfdir}/ldap/schema/hdb.schema \
	${sysconfdir}/init.d/heimdal-kdc ${sysconfdir}/default/* \
	${sysconfdir}/logrotate.d "

FILES_${BPN}-multidev += " \
	${bindir}/asn1* ${bindir}/slc \
	${bindir}/krb5-config.heimdal \
	${includedir}/${BPN}/* \
	${libdir}/${BPN}/*.so \
	${libdir}/pkgconfig/heimdal-gssapi.pc \
	${libdir}/pkgconfig/heimdal-kadm-client.pc \
	${libdir}/pkgconfig/heimdal-kadm-server.pc \
	${libdir}/pkgconfig/heimdal-krb5.pc \
	${libdir}/pkgconfig/kafs.pc \
	${libdir}/${BPN}/pkgconfig/*"

FILES_${BPN}-servers += " \
	${libdir}/${BPN}-servers/kfd"

BBCLASSEXTEND = "native nativesdk"
PARALLEL_MAKE = ""

# Install cross script to sysroot by inheriting binconfig
BINCONFIG_GLOB = "krb5-config.heimdal"
SYSROOT_PREPROCESS_FUNCS_class-target += " binconfig_sysroot_preprocess heimdal_sysroot_preprocess"
heimdal_sysroot_preprocess () {
	sed -i ${SYSROOT_DESTDIR}${bindir_crossscripts}/krb5-config.heimdal -e "s:libdir=\/usr\/lib\/heimdal:libdir=${STAGING_LIBDIR}\/heimdal:"
	sed -i ${SYSROOT_DESTDIR}${bindir_crossscripts}/krb5-config.heimdal -e "s:libdir=\/usr\/lib\/heimdal:libdir=${STAGING_INCDIR}\/heimdal:"

	# Remove these conflicts files with krb5-dev packages in sysroot
	# Using the library and header in heimdal-multidev for these packages depends on heimdal-dev
	rm -rf ${SYSROOT_DESTDIR}${libdir}/*.so \
		${SYSROOT_DESTDIR}${libdir}/pkgconfig/krb5*.pc \
		${SYSROOT_DESTDIR}${libdir}/pkgconfig/kadm*.pc
}
binconfig_sysroot_preprocess () {
	for config in `find ${D} -name '${BINCONFIG_GLOB}'` `find ${B} -name '${BINCONFIG_GLOB}'`; do
		configname=`basename $config`
		install -d ${SYSROOT_DESTDIR}${bindir_crossscripts}
		sed ${@get_binconfig_mangle(d)} $config > ${SYSROOT_DESTDIR}${bindir_crossscripts}/$configname
		chmod u+x ${SYSROOT_DESTDIR}${bindir_crossscripts}/$configname
	done
}

INSANE_SKIP_${BPN}-multidev += "dev-so"
