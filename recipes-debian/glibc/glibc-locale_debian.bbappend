# Building glibc-locale package sometimes get host-user-contaminated warning. This warning is not always reported.
# Reported file is usually different every time. 
# e.g. One build reported /glibc-binary-localedata-ar-jo/usr/lib/locale/ar_JO/LC_NAME is owned by uid 1000.
# glibc-locale-2.28-r0 do_package_qa: QA Issue: glibc-locale: /glibc-binary-localedata-ar-jo/usr/lib/locale/ar_JO/LC_NAME is owned by uid 1000, which is the same as the user running bitbake. This may be due to host contamination [host-user-contaminated]
# Other build reported /glibc-binary-localedata-ar-sy/usr/lib/locale/ar_SY/LC_MESSAGES/SYS_LC_MESSAGES is owned by uid 1000.
# glibc-locale-2.28-r0 do_package_qa: QA Issue: glibc-locale: /glibc-binary-localedata-ar-sy/usr/lib/locale/ar_SY/LC_MESSAGES/SYS_LC_MESSAGES is owned by uid 1000, which is the same as the user running bitbake. This may be due to host contamination [host-user-contaminated]

# This issue is discussed on openembedded-core[1] and a work-around has been proposed[2] for older branches although the work-around patch has not been applied yet.
# We took the approach of [2], that is to set owner and group to root for files in $treedir.
# Unfortunately, adding do_prep_locale_tree_append() in glibc-locale_debian.bbappend approach didn't fix the problem.
# So, try set user and group to file which are located in ${D}/usr/lib/locale.
# 1. https://lore.kernel.org/openembedded-core/20220616055414.42319-1-muhammad_hamza@mentor.com/T/#t
# 2. https://patches.linaro.org/project/oe-core/patch/20190207003537.7135-1-raj.khem@gmail.com/

do_install_append() {
	chown -R root:root ${D}/${localedir}
}
